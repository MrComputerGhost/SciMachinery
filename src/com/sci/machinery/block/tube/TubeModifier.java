package com.sci.machinery.block.tube;

public interface TubeModifier
{
	public static final TubeModifier SPEED = new TubeModifier()
	{
		@Override
		public void modifyTube(TubeBase tube)
		{
			tube.setSpeed(Speed.FAST);
		}
	};

	public void modifyTube(TubeBase tube);
}