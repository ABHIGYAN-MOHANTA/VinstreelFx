package org.thunderdome.vinstreelfx;

public class Friend{
    private String leetcodeUsername;
    private String githubUsername;
    private String leetcodeStats;
    private String githubStats;
    private String rank;

    public Friend(String leetcodeUsername, String githubUsername) {
        this.leetcodeUsername = leetcodeUsername;
        this.githubUsername = githubUsername;
        this.leetcodeStats = "";
        this.githubStats = "";
        this.rank = "Unranked";
    }

    public String getRank() {
        return rank;
    }

    public void calculateRank() {
        if (!githubStats.isEmpty()) {
            int contributions = Integer.parseInt(githubStats);
            if (contributions >= 1000) {
                rank = "Elite";
            } else if (contributions >= 500) {
                rank = "Advanced";
            } else if (contributions >= 100) {
                rank = "Intermediate";
            } else {
                rank = "Beginner";
            }
        }
    }
    public String getLeetcodeUsername() {
        return leetcodeUsername;
    }

    public void setLeetcodeUsername(String leetcodeUsername) {
        this.leetcodeUsername = leetcodeUsername;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getLeetcodeStats() {
        return leetcodeStats;
    }

    public void setLeetcodeStats(String leetcodeStats) {
        this.leetcodeStats = leetcodeStats;
    }

    public String getGithubStats() {
        return githubStats;
    }

    public void setGithubStats(String githubStats) {
        this.githubStats = githubStats;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "leetcodeUsername='" + leetcodeUsername + '\'' +
                ", githubUsername='" + githubUsername + '\'' +
                ", leetcodeStats='" + leetcodeStats + '\'' +
                ", githubStats='" + githubStats + '\'' +
                '}';
    }

}
