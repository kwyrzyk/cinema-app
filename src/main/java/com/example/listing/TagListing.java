package com.example.listing;

import java.sql.Connection;
import java.util.List;

import com.example.database.db_classes.Tag;
import com.example.database.TagsRepository;

public class TagListing {
    List<Tag> tags;

    /**
     * Constructor initializes the tags list.
     * @param connection the database connection
     */
    public TagListing(Connection connection) {
        this.tags = TagsRepository.getAllTags(connection);
    }

    /**
     * Get the list of tags.
     * @return a list of tags
     */
    public List<Tag> getTags() {
        return tags;
    }
}
