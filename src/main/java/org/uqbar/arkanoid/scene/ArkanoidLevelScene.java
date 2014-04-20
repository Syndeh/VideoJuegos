package org.uqbar.arkanoid.scene;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arkanoid.components.Sight;
import org.uqbar.arkanoid.components.Ball;
import org.uqbar.arkanoid.components.LifeAward;
import org.uqbar.arkanoid.components.LivesCounter;
import org.uqbar.arkanoid.components.Paddle;
import org.uqbar.arkanoid.components.PointsCounter;
import org.uqbar.arkanoid.components.SpeedMeter;
import org.uqbar.arkanoid.components.StaticBlock;

import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;

public abstract class ArkanoidLevelScene extends GameScene {

	
	private LivesCounter livesCounter;
	private PointsCounter pointCounter;
	private SpeedMeter speedMeter;
	
	private Ball ball;
	private Paddle paddleBlock;
	private final List<StaticBlock> staticBlocks = new ArrayList<StaticBlock>();
	private List<LifeAward> lifeAwards = new ArrayList<LifeAward>();
	private Sight arrowSight = new Sight();

	
	@Override
	public void onSetAsCurrent() {
		this.initializeComponents();
		super.onSetAsCurrent();
	}

	/**
	 * Ventana de ejecución para la inicialización de los componentes.
	 */
	protected void initializeComponents() {
		this.initializePaddleBlock();
		this.initializeBall();
		this.initializeBlocks();
		this.initializePointsCounter();
		this.initializeLivesCounter();
		this.initializeSpeedMeter();
		this.initializeArrowSight();
	}

	protected abstract void initializePaddleBlock();
	
	protected abstract void initializeBall();
	
	protected abstract void initializeBlocks();

	private void initializePointsCounter() {
		this.setPointCounter(new PointsCounter());
		this.getPointCounter().alignHorizontalCenterTo(this.getGame().getDisplayWidth()/2);
		this.getPointCounter().setY(5);
		this.addComponent(this.getPointCounter());
	}
	
	private void initializeLivesCounter() {
		this.setLivesCounter(new LivesCounter());
		this.getLivesCounter().setX(5);
		this.getLivesCounter().setY(5);
		this.addComponent(this.getLivesCounter());
	}
	
	private void initializeSpeedMeter() {
		this.setSpeedMeter(new SpeedMeter());
		this.getSpeedMeter().setX(5);
		this.getSpeedMeter().setY(this.getGame().getDisplayHeight() - 20);
		this.addComponent(this.getSpeedMeter());
	}
	
	private void initializeArrowSight(){
		this.setArrowSight(new Sight());
		this.getArrowSight().setX(this.getPaddleBlock().obtainXCenter() -1);
		this.getArrowSight().setY(this.getPaddleBlock().getY() - 50);
		this.addComponent(this.getArrowSight());
	}

	public Ball getBall() {
		return this.ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
		this.addComponent(ball);
	}

	public Paddle getPaddleBlock() {
		return this.paddleBlock;
	}

	public void setPaddleBlock(Paddle paddleBlock) {
		this.paddleBlock = paddleBlock;
		this.addComponent(this.paddleBlock);
	}
	
	protected PointsCounter getPointCounter() {
		return this.pointCounter;
	}

	protected void setPointCounter(PointsCounter pointCounter) {
		this.pointCounter = pointCounter;
	}
	
	public void addPoint() {
		this.getPointCounter().addPoint();
	}
	
	public void addPoints(int points) {
		this.getPointCounter().addPoints(points);
	}

	public LivesCounter getLivesCounter() {
		return this.livesCounter;
	}

	public void setLivesCounter(LivesCounter livesCounter) {
		this.livesCounter = livesCounter;
	}
	
	public void loseLife() {
		if (this.getLivesCounter().getLives() > 0) {
			this.getLivesCounter().removeLife();
			this.resetComponents();
			this.initializeArrowSight();
		} else {
			this.getBall().destroy();
			this.getGame().setCurrentScene(new ArkanoidGameOverScene());
		}
	}

	protected void resetComponents() {
		this.getPaddleBlock().destroy();
		this.getBall().destroy();
		
		for (LifeAward lifeAward : this.getLifeAwards()) {
			lifeAward.destroy();
		}
		
		this.initializePaddleBlock();
		this.initializeBall();
		this.initializeArrowSight();
	}

	protected SpeedMeter getSpeedMeter() {
		return this.speedMeter;
	}

	protected void setSpeedMeter(SpeedMeter speedMeter) {
		this.speedMeter = speedMeter;
	}

	protected void addBlock(StaticBlock block) {
		this.staticBlocks.add(block);
		this.addComponent(block);
	}
	
	public void addLifeAward(LifeAward lifeAward){
		this.getLifeAwards().add(lifeAward);
		this.addComponent(lifeAward);
	}

	@Override
	public void removeComponent(GameComponent<?> component) {
		this.staticBlocks.remove(component);
		component.destroy();
		if(this.staticBlocks.isEmpty()) {
			this.resetComponents();
			this.initializeBlocks();
		}
		super.removeComponent(component);
	}

	protected List<LifeAward> getLifeAwards() {
		return lifeAwards;
	}

	protected void setLifeAwards(List<LifeAward> lifeAwards) {
		this.lifeAwards = lifeAwards;
	}

	public Sight getArrowSight() {
		return arrowSight;
	}

	public void setArrowSight(Sight arrowSight) {
		this.arrowSight = arrowSight;
	}
}
