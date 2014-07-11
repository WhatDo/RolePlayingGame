package dk.sdc.rpg;

import android.graphics.*;
import android.graphics.Paint.Style;

public abstract class Creature {

	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	
	private static final int HEALTHBAR_HEIGHT = 20;

	private int mMaxHealth;
	private int mHealth;
	private int mAgility;
	private int mStrength;
	private int mIntelligence;
	
	private Opgave mOpgave = new Opgave();

	private Bitmap mBitmap;
	private int mX, mY;

	private Matrix mMatrix;

	private int mFacing = RIGHT;
	
	private Rect mHealthBar;
	private Paint mHealthPaint;

	public Creature(int health, int agility, int strength, int intelligence) {
		mMaxHealth = health;
		mHealth = health;
		mAgility = agility;
		mStrength = strength;
		mIntelligence = intelligence;

		mMatrix = new Matrix();
		mHealthBar = new Rect();

		mHealthPaint = new Paint();
		mHealthPaint.setColor(0xAA5F0000);
		mHealthPaint.setStyle(Style.FILL);
		mHealthPaint.setStrokeWidth(0);
	}

	public void init(Bitmap bitmap) {
		this.mBitmap = bitmap;
	}

	public void draw(Canvas canvas) {
		mMatrix.reset();
		mMatrix.setTranslate(mX, mY);
		mMatrix.preScale(0.2f * mFacing, 0.2f);
		canvas.drawBitmap(mBitmap, mMatrix, null);
		
		float healthleft = ((float) mHealth) / mMaxHealth;
		int w = canvas.getWidth();
		int h = canvas.getHeight();
		
		if (mFacing == LEFT) {
			mHealthBar.set((int) (w / 2 + w / 2 * (1 - healthleft)), 0, w, h / HEALTHBAR_HEIGHT);
		} else {
			mHealthBar.set(0, 0, (int) (w / 2 * healthleft), h / HEALTHBAR_HEIGHT);
		}
		
		canvas.drawRect(mHealthBar, mHealthPaint);
	}
	
	public void move(int dist) {
		mX += mFacing *dist;
	}

	public int getHealth() {
		return mHealth;
	}

	public void setHealth(int health) {
		this.mHealth = health;
	}

	public int getAgility() {
		return mAgility;
	}

	public void setAgility(int agility) {
		this.mAgility = agility;
	}

	public int getStrength() {
		return mStrength;
	}

	public void setStrength(int strength) {
		this.mStrength = strength;
	}

	public int getIntelligence() {
		return mIntelligence;
	}

	public void setIntelligence(int intelligence) {
		this.mIntelligence = intelligence;
	}

	public int getX() {
		return mX;
	}

	public void setX(int x) {
		this.mX = x;
	}

	public int getY() {
		return mY;
	}

	public void setY(int y) {
		this.mY = y;
	}
	
	public void setFacing(int dir) {
		mFacing = dir;
	}

	public void reset() {
		mHealth = mMaxHealth;
	}

	public int getDamage() {
		return mOpgave.calculateDamageToDeal(this);
	}
	
	public int getStrongDamage() {
		return mOpgave.calculateStrongDamageToDeal(this);
	}

	public void takeDamage(int damage) {
		mHealth -= mOpgave.calculateDamageTaken(this, damage);
	}

}