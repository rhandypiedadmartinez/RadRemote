package com.example.radremote;

import java.util.ArrayList;
import java.util.List;

public class Remote {
    protected String remoteName;
    protected List<RemoteButton> remoteButtons;
    protected List<String> remoteButtonNames;

    public Remote(){
        this.remoteButtons = new ArrayList<RemoteButton>();
        this.remoteButtonNames = new ArrayList<String>();
    }

    public void addButton(RemoteButton remoteButton){
        remoteButtons.add(remoteButton);
        remoteButtonNames.add(remoteButton.buttonName);
    }

    public RemoteButton getRemoteButton(int index){
        return remoteButtons.get(index);
    }

    public RemoteButton getRemoteButton(String buttonName){
        return getRemoteButton(remoteButtonNames.indexOf(buttonName));
    }

    public int[] getPattern(String buttonName){
        return getRemoteButton(buttonName).rawIrPattern;
    }
}
