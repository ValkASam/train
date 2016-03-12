package pattern.Command;

import org.w3c.dom.Entity;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Valk on 06.01.16.
 */
public class CommandTest {
    public static void main(String[] args) {
        Invoker<Lamp> lampInvoker = new Invoker<>(new Lamp());
        Invoker<Tv> tvInvoker = new Invoker<>(new Tv()); //Invoker<Tv> не позволит сделать так tvInvoker.setDevice(new Lamp());
        //
        lampInvoker.onDevice();
        lampInvoker.offDevice();
        //
        tvInvoker.onDevice();
        tvInvoker.offDevice();
        //
        lampInvoker.setDevice(new BlinkingLamp());
        lampInvoker.onDevice();
        lampInvoker.offDevice();
    }

}

interface Device {
    void on();

    void off();
}

class Lamp implements Device {
    @Override
    public void on() {
        System.out.println("lamp is on");
    }

    @Override
    public void off() {
        System.out.println("lamp is off");
    }
}

class BlinkingLamp extends Lamp {
    @Override
    public void on() {
        System.out.println("lamp is blinking");
    }

    @Override
    public void off() {
        System.out.println("lamp is off");
    }
}

class Tv implements Device {
    @Override
    public void on() {
        System.out.println("tv is on");
    }

    @Override
    public void off() {
        System.out.println("tv is off");
    }
}

interface Command {
    void execute();
}

class OnCommand implements Command {
    private Device device;

    public OnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.on();
    }
}

class OffCommand implements Command {
    private Device device;

    public OffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.off();
    }
}

class Invoker<T extends Device> {
    private Command onCommand;
    private Command offCommand;

    public Invoker(T device) {
        this.onCommand = new OnCommand(device);
        this.offCommand = new OffCommand(device);
    }

    public void setDevice (T  device){
        this.onCommand = new OnCommand(device);
        this.offCommand = new OffCommand(device);
    }

    public void onDevice() {
        onCommand.execute();
    }

    public void offDevice() {
        offCommand.execute();
    }
}

