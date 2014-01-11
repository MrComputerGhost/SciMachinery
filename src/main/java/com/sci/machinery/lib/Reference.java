package com.sci.machinery.lib;

import java.io.InputStream;
import java.util.Properties;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class Reference
{
	static
	{
		Properties prop = new Properties();

		try
		{
			InputStream stream = Reference.class.getClassLoader().getResourceAsStream("version.properties");
			prop.load(stream);
			stream.close();
		}
		catch(Exception e)
		{

		}

		MOD_VERSION = prop.getProperty("version") + " (build " + prop.getProperty("buildnumber") + ")";
	}

	public static final String CHANNEL_NAME = "SciMachinery";

	public static final String CLIENT_PROXY = "com.sci.machinery.core.ClientProxy";
	public static final String MOD_ID = "SciMachinery";
	public static final String MOD_NAME = "SciMachinery";
	public static final String MOD_VERSION;

	public static final String SERVER_PROXY = "com.sci.machinery.core.CommonProxy";

	private Reference()
	{
	}
}
