package com.example.orbital_layoutfrontend;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Objects;

public class OldTeam {
    ArrayList<OldPlayer> oldPlayers = new ArrayList<>();
    String name;

    public OldTeam(String name, ArrayList<OldPlayer> oldPlayers) {
        this.name = name;
        this.oldPlayers = oldPlayers;
    }

    public String getName() {
        return name;
    }

    public ArrayList<OldPlayer> getPlayers() {
        return oldPlayers;
    }

    public String toString() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldTeam oldTeam = (OldTeam) o;
        return Objects.equals(name, oldTeam.name);
    }
}
