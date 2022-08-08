package com.example.radremote;

import java.util.ArrayList;
import java.util.List;

public class Remote {
    String remoteName;
    List remoteButtons;

    public Remote(String remoteName){
        this.remoteName = remoteName;
        this.remoteButtons = new ArrayList<RemoteButton>();
    }
}
