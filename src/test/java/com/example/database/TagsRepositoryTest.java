package com.example.database;

import com.example.database.db_classes.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TagsRepositoryTest {

    private static DatabaseManager databaseManager = new DatabaseManager();

    @Test
    public void testGetAllTags() throws SQLException {
        // Test retrieving all tags
        List<Tag> tags = TagsRepository.getAllTags(databaseManager.getConnection());

        // Assert the tags list is not null and has at least one tag
        assertNotNull(tags, "Tags list should not be null");
        assertTrue(tags.size() > 0, "There should be at least one tag in the list");

        // Assert each tag in the list is not null
        for (Tag tag : tags) {
            assertNotNull(tag, "Tag should not be null");
            assertNotNull(tag.getName(), "Tag name should not be null");
            assertTrue(tag.getName().length() > 0, "Tag name should not be empty");
            assertTrue(tag.getId() > 0, "Tag ID should be greater than 0");
        }
    }

}
