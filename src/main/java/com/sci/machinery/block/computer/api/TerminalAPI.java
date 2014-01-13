package com.sci.machinery.block.computer.api;

import com.sci.machinery.block.computer.api.OSAPI.OSAPIMethod;

/**
 * SciMachinery
 * 
 * @author hawks008
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class TerminalAPI {
	
	@OSAPIMethod(apiMain = "os")
	public String shutdown() {
		return "down";
	}

}
