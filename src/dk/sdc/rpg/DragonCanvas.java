package dk.sdc.rpg;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DragonCanvas extends View {

	private static String TAG = "Canvas";

	private static final int BASE_HEALTH_HUMAN = 30;
	private static final int BASE_HEALTH_ORC = 35;
	private static final int BASE_HEALTH_DRAGON = 45;

	private static final int BASE_STRENGTH_HUMAN = 20;
	private static final int BASE_STRENGTH_ORC = 25;
	private static final int BASE_STRENGTH_DRAGON = 30;

	private static final int BASE_AGILITY_HUMAN = 25;
	private static final int BASE_AGILITY_ORC = 20;
	private static final int BASE_AGILITY_DRAGON = 25;

	private static final int BASE_INTELLIGENCE_HUMAN = 20;
	private static final int BASE_INTELLIGENCE_ORC = 10;
	private static final int BASE_INTELLIGENCE_DRAGON = 30;

	private static String OPPONENT = "Opponent";
	private static String PLAYER = "Player";

	private static final int KEY_STRONG = 0x1;
	private static final int KEY_NORMAL = 0x2;

	private Random mRandom = new Random();

	private List<Creature> mCreatures = new ArrayList<Creature>();

	private Bitmap mBackground;
	private Bitmap mHumanBitmap;
	private Bitmap mOrc;
	private Bitmap[] mDragons;

	private Paint mTextPaint;

	private Creature mPlayer;
	private Creature mOpponent;

	private Opgave mOpgave = new Opgave();

	private String mCurrentTurn = PLAYER;
	private String mOtherTurn = OPPONENT;

	private int mWidth;
	private int mHeight;

	private int mDistance;

	private KeyPress mKeyPress = new KeyPress();

	private Executor mExe = Executors.newSingleThreadExecutor();

	public DragonCanvas(Context context) {
		this(context, null);
	}

	public DragonCanvas(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragonCanvas(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		startLoop();
	}

	private void init() {

		mPlayer = new Human(40, 30, 30, 30);

		mDragons = new Bitmap[3];

		mHumanBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.knight);
		mOrc = BitmapFactory.decodeResource(getResources(), R.drawable.orc);
		Bitmap dragonBlue = BitmapFactory.decodeResource(getResources(),
				R.drawable.dragon_blue);
		Bitmap dragonGreen = BitmapFactory.decodeResource(getResources(),
				R.drawable.dragon_green);
		Bitmap dragonRed = BitmapFactory.decodeResource(getResources(),
				R.drawable.dragon_red);
		mBackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg);

		mDragons[0] = dragonBlue;
		mDragons[1] = dragonGreen;
		mDragons[2] = dragonRed;

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(Color.WHITE);
		int size = getResources().getDimensionPixelSize(R.dimen.fontsize_turn);
		mTextPaint.setTextSize(size);

		mPlayer = rollChar();
		mOpponent = rollChar();
		resetLevel();
	}

	private void resetLevel() {
		resetPlayers();
		post(new Runnable() {
			@Override
			public void run() {
				startLoop();
			}
		});
	}

	private void resetPlayers() {
		mCurrentTurn = PLAYER;
		mOtherTurn = OPPONENT;
		mCreatures.clear();

		mOpponent.setFacing(Creature.LEFT);

		mOpponent.setX((int) (mWidth * 3 / 4.0f));
		mOpponent.setY((int) (mHeight / 2.0f - mHumanBitmap.getHeight() * 0.2f / 4.0f));

		mPlayer.setX((int) (mWidth / 4.0f));
		mPlayer.setY((int) (mHeight / 2.0f - mHumanBitmap.getHeight() * 0.2f / 4.0f));

		mCreatures.add(mOpponent);
		mCreatures.add(mPlayer);
	}

	private Creature rollChar() {
		Creature creature = mOpgave.createCreature();
		if (creature != null) {
			creature.init(getContext());
			return creature;
		}

		int image;
		int opp = (int) (Math.random() * 3);
		switch (opp) {
			case 0:
				creature = new Human(
						(int) (BASE_HEALTH_HUMAN + mRandom.nextGaussian() * 5),
						(int) (BASE_AGILITY_HUMAN + mRandom.nextGaussian() * 5),
						(int) (BASE_STRENGTH_HUMAN + mRandom.nextGaussian() * 5),
						(int) (BASE_INTELLIGENCE_HUMAN + mRandom.nextGaussian() * 5));
				image = R.drawable.knight;
				break;

			case 1:
				creature = new Orc(
						(int) (BASE_HEALTH_ORC + mRandom.nextGaussian() * 5),
						(int) (BASE_AGILITY_ORC + mRandom.nextGaussian() * 5),
						(int) (BASE_STRENGTH_ORC + mRandom.nextGaussian() * 5),
						(int) (BASE_INTELLIGENCE_ORC + mRandom.nextGaussian() * 5));
				image = R.drawable.orc;
				break;

			case 2:
				creature = new Dragon(
						(int) (BASE_HEALTH_DRAGON + mRandom.nextGaussian() * 5),
						(int) (BASE_AGILITY_DRAGON + mRandom.nextGaussian() * 5),
						(int) (BASE_STRENGTH_DRAGON + mRandom.nextGaussian() * 5),
						(int) (BASE_INTELLIGENCE_DRAGON + mRandom.nextGaussian() * 5));
				int[] images = {R.drawable.dragon_blue, R.drawable.dragon_red, R.drawable.dragon_green};
				image = images[((int) (Math.random() * images.length))];
				break;

			default:
				throw new RuntimeException("Lel exception");
		}
		creature.init(getContext());
		creature.setBitmap(image);
		return creature;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);

		canvas.drawBitmap(mBackground, 0, 0, null);

		String turn = mCurrentTurn + "'s turn";
		int textlength = (int) mTextPaint.measureText(turn);

		canvas.drawText(turn, canvas.getWidth() / 2 - textlength / 2,
				canvas.getHeight() / 8, mTextPaint);

		for (Creature creature : mCreatures) {
			creature.draw(canvas);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();

		mPlayer.setX((int) (mWidth / 4.0f));
		mPlayer.setY((int) (mHeight / 2.0f - mHumanBitmap.getHeight() * 0.2f / 4.0f));
		mOpponent.setX((int) (mWidth * 3 / 4.0f));
		mOpponent.setY((int) (mHeight / 2.0f - mHumanBitmap.getHeight() * 0.2f / 4.0f));
	}

	public void attackClicked() {
		mKeyPress.key = KEY_NORMAL;
		mKeyPress.time = System.currentTimeMillis();
	}

	public void strongAttackClicked() {
		mKeyPress.key = KEY_STRONG;
		mKeyPress.time = System.currentTimeMillis();
	}

	private void nextTurn() {
		String temp = mCurrentTurn;
		mCurrentTurn = mOtherTurn;
		mOtherTurn = temp;
	}

	private void startLoop() {
		mExe.execute(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// Shomehow detect presses
					int key;
					while (true) {
						if (mKeyPress.time + 15 > System.currentTimeMillis()) {
							key = mKeyPress.key;
							break;
						}
						try {
							Thread.sleep(14);
						} catch (InterruptedException ignored) {
						}
					}

					int distance = key == KEY_NORMAL ? mWidth / 3 - 20 : mWidth / 3 - 10;

					mPlayer.move(distance);
					postInvalidate();

					boolean win = key == KEY_NORMAL ? mOpgave.attack(mPlayer, mOpponent) :
							mOpgave.strongAttack(mPlayer, mOpponent);
					if (win) {
						post(new Runnable() {
							@Override
							public void run() {
								win();
							}
						});
						return;
					}

					try {
						Thread.sleep(700);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					mPlayer.move(-distance);
					nextTurn();

					postInvalidate();

					// Opponent
					pause(700);

					int action = (int) (Math.random() * 2);
					switch (action) {
						case 0:
							mDistance = mWidth / 3 - 20;
							mOpponent.move(mDistance);
							postInvalidate();

							if (mOpgave.attack(mOpponent,
									mPlayer)) {
								post(new Runnable() {
									@Override
									public void run() {
										lose();
									}
								});
								return;
							}
							break;

						case 1:
							mDistance = mWidth / 3 - 10;
							mOpponent.move(mDistance);
							postInvalidate();

							if (mOpgave.strongAttack(mOpponent,
									mPlayer)) {
								post(new Runnable() {
									@Override
									public void run() {
										lose();
									}
								});
								return;
							}
							break;
					}

					postInvalidate();

					pause(700);

					mOpponent.move(-mDistance);
					nextTurn();

					postInvalidate();

					pause(300);
				}
			}
		});
	}

	private void pause(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException ignored) {
		}
	}

	public void win() {
		Toast.makeText(getContext(), "Tillykke, du vandt dette level",
				Toast.LENGTH_LONG).show();

		resetLevel();
		invalidate();
	}

	public void lose() {
		Toast.makeText(getContext(), "Du tabte, din taber", Toast.LENGTH_LONG)
				.show();

		resetLevel();
		invalidate();
	}

	private static class KeyPress {
		int key;
		long time;

		public void set(int key) {
			this.key = key;
			time = System.currentTimeMillis();
		}
	}
}
