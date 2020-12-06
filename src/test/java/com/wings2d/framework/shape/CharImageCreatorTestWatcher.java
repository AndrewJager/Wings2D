package com.wings2d.framework.shape;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.wings2d.framework.CharImageCreatorTest;

public class CharImageCreatorTestWatcher implements TestWatcher{
	@Override
	public void testFailed(ExtensionContext context, Throwable cause)
	{
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		BufferedImage img = testInstance.getGeneratedImages().get(context.getRequiredTestMethod().getName());
		testInstance.getErrorImages().put(context.getRequiredTestMethod().getName(), img);
	}
}
