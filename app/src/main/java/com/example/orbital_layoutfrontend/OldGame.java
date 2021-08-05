package com.example.orbital_layoutfrontend;

import java.io.Serializable;
import java.util.Stack;

public class OldGame implements Serializable {
    private final int catches;
    private final int failedCatches;
    private final int totalPasses;
    private final int successfulPasses;
    private final int totalCuts;
    private final int successfulCuts;
    private final String score;
    private Stack gameHistory;
    private Stack timeHistory;

    public OldGame(int catches, int failedCatches, int totalPasses, int successfulPasses,
                   int totalCuts, int successfulCuts, String score, Stack gameHistory, Stack timeHistory) {
        this.catches = catches;
        this.failedCatches = failedCatches;
        this.totalPasses = totalPasses;
        this.successfulPasses = successfulPasses;
        this.totalCuts = totalCuts;
        this.successfulCuts = successfulCuts;
        this.score = score;
        this.gameHistory = gameHistory;
        this.timeHistory = timeHistory;
    }

    public OldGame(int catches, int failedCatches, int totalPasses, int successfulPasses,
                   int totalCuts, int successfulCuts, String score) {
        this.catches = catches;
        this.failedCatches = failedCatches;
        this.totalPasses = totalPasses;
        this.successfulPasses = successfulPasses;
        this.totalCuts = totalCuts;
        this.successfulCuts = successfulCuts;
        this.score = score;
    }

    public int getCatches() {
        return catches;
    }

    public int getFailedCatches() {
        return failedCatches;
    }

    public int getTotalPasses() {
        return totalPasses;
    }

    public int getSuccessfulPasses() {
        return successfulPasses;
    }

    public int getTotalCuts() {
        return totalCuts;
    }

    public int getSuccessfulCuts() {
        return successfulCuts;
    }

    public String getScore() {
        return score;
    }

    public Stack getGameHistory() {
        return gameHistory;
    }

    public Stack getTimeHistory() {
        return timeHistory;
    }
}