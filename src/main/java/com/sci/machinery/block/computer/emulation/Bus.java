package com.sci.machinery.block.computer.emulation;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public final class Bus
{
	private SortedSet<Device> devices;

	public Bus()
	{
		this.devices = new TreeSet<Device>();
	}

	public void addDevice(Device device) throws MemoryException
	{
		MemoryRange memRange = device.getMemoryRange();
		for(Device d : this.devices)
		{
			if(d.getMemoryRange().overlaps(memRange))
				throw new MemoryException("The device being added at " + String.format("$%04X", memRange.getStartAddress()) + " overlaps with an existing device, '" + device + "'");
		}

		device.setBus(this);
		devices.add(device);
	}

	public void removeDevice(Device device)
	{
		if(this.devices.contains(device))
			this.devices.remove(device);
	}

	public int read(int address) throws MemoryException
	{
		for(Device d : this.devices)
		{
			MemoryRange range = d.getMemoryRange();
			if(range.includes(address))
			{
				int devAddr = address - range.getStartAddress();
				return d.read(devAddr);
			}
		}
		throw new MemoryException("Bus read: No device at address " + String.format("$%04X", address));
	}

	public void write(int address, int value) throws MemoryException
	{
		for(Device d : this.devices)
		{
			MemoryRange range = d.getMemoryRange();
			if(range.includes(address))
			{
				int devAddr = address - range.getStartAddress();
				d.write(devAddr, value);
				return;
			}
		}
		throw new MemoryException("Bus write: No device at address " + String.format("$%04X", address));
	}

	public SortedSet<Device> getDevices()
	{
		return new TreeSet<Device>(this.devices);
	}
}