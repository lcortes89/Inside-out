package org.luisa.diary.models;

import java.time.LocalDate;

/**
 * Represents a lived moment registered by the user in the diary.
 */
public class Moment {

    private int id;
    private String title;
    private String description;
    private Emotion emotion;
    private LocalDate momentDate;
    private final LocalDate creationDate;
    private LocalDate modificationDate;

    /**
     * Creates a new moment. The id is assigned later by the repository,
     * and the creation/modification dates are set to today.
     *
     * @param title the moment's title
     * @param description the moment's description
     * @param emotion the emotion associated with the moment
     * @param momentDate the date the moment happened
     */
    public Moment(String title, String description, Emotion emotion, LocalDate momentDate) {
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.momentDate = momentDate;
        this.creationDate = LocalDate.now();
        this.modificationDate = LocalDate.now();
    }

    public int getId() {
        return this.id;
    }

    /**
     * Assigns the moment's unique identifier. Meant to be called once,
     * by the repository, when the moment is first saved.
     *
     * @param id the identifier to assign
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Emotion getEmotion() {
        return this.emotion;
    }

    public LocalDate getMomentDate() {
        return this.momentDate;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public LocalDate getModificationDate() {
        return this.modificationDate;
    }

}
