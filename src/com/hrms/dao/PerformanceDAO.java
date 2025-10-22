package com.hrms.dao;

import com.hrms.util.DBConnection;
import com.hrms.model.PerformanceReview;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerformanceDAO {

    // Add performance review
    public void addPerformanceReview(PerformanceReview review) {
        String query = "INSERT INTO performance_reviews (employee_id, reviewer_id, review_period_start, review_period_end, overall_rating, goals_achieved, areas_for_improvement, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, review.getEmployeeId());
            ps.setInt(2, review.getReviewerId());
            ps.setDate(3, review.getReviewPeriodStart());
            ps.setDate(4, review.getReviewPeriodEnd());
            ps.setDouble(5, review.getOverallRating());
            ps.setString(6, review.getGoalsAchieved());
            ps.setString(7, review.getAreasForImprovement());
            ps.setString(8, review.getComments());

            ps.executeUpdate();
            System.out.println("✅ Performance review added for employee ID: " + review.getEmployeeId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all performance reviews
    public List<PerformanceReview> getAllPerformanceReviews() {
        List<PerformanceReview> reviews = new ArrayList<>();
        String query = "SELECT * FROM performance_reviews ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                PerformanceReview review = new PerformanceReview(
                        rs.getInt("review_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("reviewer_id"),
                        rs.getDate("review_period_start"),
                        rs.getDate("review_period_end"),
                        rs.getDouble("overall_rating"),
                        rs.getString("goals_achieved"),
                        rs.getString("areas_for_improvement"),
                        rs.getString("comments")
                );
                reviews.add(review);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    // Get performance reviews by employee ID
    public List<PerformanceReview> getPerformanceReviewsByEmployee(int empId) {
        List<PerformanceReview> reviews = new ArrayList<>();
        String query = "SELECT * FROM performance_reviews WHERE employee_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(new PerformanceReview(
                        rs.getInt("review_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("reviewer_id"),
                        rs.getDate("review_period_start"),
                        rs.getDate("review_period_end"),
                        rs.getDouble("overall_rating"),
                        rs.getString("goals_achieved"),
                        rs.getString("areas_for_improvement"),
                        rs.getString("comments")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    // Get performance reviews by reviewer ID
    public List<PerformanceReview> getPerformanceReviewsByReviewer(int reviewerId) {
        List<PerformanceReview> reviews = new ArrayList<>();
        String query = "SELECT * FROM performance_reviews WHERE reviewer_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, reviewerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(new PerformanceReview(
                        rs.getInt("review_id"),
                        rs.getInt("employee_id"),
                        rs.getInt("reviewer_id"),
                        rs.getDate("review_period_start"),
                        rs.getDate("review_period_end"),
                        rs.getDouble("overall_rating"),
                        rs.getString("goals_achieved"),
                        rs.getString("areas_for_improvement"),
                        rs.getString("comments")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    // Update performance review
    public boolean updatePerformanceReview(PerformanceReview review) {
        String query = "UPDATE performance_reviews SET overall_rating = ?, goals_achieved = ?, areas_for_improvement = ?, comments = ? WHERE review_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDouble(1, review.getOverallRating());
            ps.setString(2, review.getGoalsAchieved());
            ps.setString(3, review.getAreasForImprovement());
            ps.setString(4, review.getComments());
            ps.setInt(5, review.getReviewId());
            
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get average rating for employee
    public double getAverageRatingForEmployee(int empId) {
        String query = "SELECT AVG(overall_rating) as avg_rating FROM performance_reviews WHERE employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}