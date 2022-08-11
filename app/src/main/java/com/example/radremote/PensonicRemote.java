package com.example.radremote;

public class PensonicRemote extends Remote{
    public PensonicRemote(){
        super();
        super.addButton(new RemoteButton("Power","00FE", "50AF"));
        super.addButton(new RemoteButton("1","00FE", "708F"));
        super.addButton(new RemoteButton("2","00FE", "7A85"));
        super.addButton(new RemoteButton("3","00FE", "F00F"));
        super.addButton(new RemoteButton("4","00FE", "48B7"));
        //rmt.addButton(new RemoteButton("5","00FE", "hinahanap ko pa"));
        super.addButton(new RemoteButton("6","00FE", "C837"));
        super.addButton(new RemoteButton("7","00FE", "6897"));
        super.addButton(new RemoteButton("8","00FE", "40BF"));
        super.addButton(new RemoteButton("9","00FE", "E817"));
       //super.addButton(new RemoteButton("0","00FE", "hinahanap ko pa"));
        super.addButton(new RemoteButton("VOL+","00FE", "F807"));
        super.addButton(new RemoteButton("VOL-","00FE", "FA05"));
        super.addButton(new RemoteButton("MUTE","00FE", "D02F"));
        super.addButton(new RemoteButton("EXIT","00FE", "9A65"));
        super.addButton(new RemoteButton("MENU","00FE", "2AD5"));
        super.addButton(new RemoteButton("UP","00FE","7A85"));
        super.addButton(new RemoteButton("DOWN","00FE", "6A95"));
        super.addButton(new RemoteButton("RIGHT","00FE", "1AE5"));
        super.addButton(new RemoteButton("LEFT","00FE", "DA25"));
        super.addButton(new RemoteButton("CLICK","00FE", "5AA5"));
        super.addButton(new RemoteButton("LEFT","00FE", "DA25"));
        super.addButton(new RemoteButton("INPUTSRC","00FE","CA35"));
        
    }
}
