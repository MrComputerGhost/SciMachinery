package com.sci.machinery.block.tube;

public interface TubeModifier
{
	public static final TubeModifier SPEED_UP = new TubeModifier()
	{
		@Override
		public void modifyTube(TubeBase tube)
		{
			tube.setSpeed(Speed.FAST);
		}
	};

	public static final TubeModifier SPEED_NORMAL = new TubeModifier()
	{
		@Override
		public void modifyTube(TubeBase tube)
		{
			tube.setSpeed(Speed.MEDIUM);
		}
	};

	public static final TubeModifier SPEED_DOWN = new TubeModifier()
	{
		@Override
		public void modifyTube(TubeBase tube)
		{
			tube.setSpeed(Speed.SLOW);
		}
	};

	public void modifyTube(TubeBase tube);
}