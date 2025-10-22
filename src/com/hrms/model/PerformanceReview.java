package com.hrms.model;

import java.sql.Date;

public class PerformanceReview {
    private int reviewId;
    private int employeeId;
    private int reviewerId;
    private Date reviewPeriodStart;
    private Date reviewPeriodEnd;
    private double overallRating;
    private String goalsAchieved;
    private String areasForImprovement;
    private String comments;

    public PerformanceReview(int reviewId, int employeeId, int reviewerId, Date reviewPeriodStart, Date reviewPeriodEnd, double overallRating, String goalsAchieved, String areasForImprovement, String comments) {
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.reviewerId = reviewerId;
        this.reviewPeriodStart = reviewPeriodStart;
        this.reviewPeriodEnd = reviewPeriodEnd;
        this.overallRating = overallRating;
        this.goalsAchieved = goalsAchieved;
        this.areasForImprovement = areasForImprovement;
        this.comments = comments;
    }

    // Getters and Setters
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getReviewerId() { return reviewerId; }
    public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }

    public Date getReviewPeriodStart() { return reviewPeriodStart; }
    public void setReviewPeriodStart(Date reviewPeriodStart) { this.reviewPeriodStart = reviewPeriodStart; }

    public Date getReviewPeriodEnd() { return reviewPeriodEnd; }
    public void setReviewPeriodEnd(Date reviewPeriodEnd) { this.reviewPeriodEnd = reviewPeriodEnd; }

    public double getOverallRating() { return overallRating; }
    public void setOverallRating(double overallRating) { this.overallRating = overallRating; }

    public String getGoalsAchieved() { return goalsAchieved; }
    public void setGoalsAchieved(String goalsAchieved) { this.goalsAchieved = goalsAchieved; }

    public String getAreasForImprovement() { return areasForImprovement; }
    public void setAreasForImprovement(String areasForImprovement) { this.areasForImprovement = areasForImprovement; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "PerformanceReview [reviewId=" + reviewId + ", employeeId=" + employeeId + 
               ", reviewerId=" + reviewerId + ", reviewPeriodStart=" + reviewPeriodStart + 
               ", reviewPeriodEnd=" + reviewPeriodEnd + ", overallRating=" + overallRating + 
               ", goalsAchieved=" + goalsAchieved + ", areasForImprovement=" + areasForImprovement + 
               ", comments=" + comments + "]";
    }
}