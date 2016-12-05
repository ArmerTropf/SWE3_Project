package de.hsb.webapp.bc.model;

/**
 * With this enumeration you can handle the genre types for the books.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 * 
 */
public enum GenreType {
	// List of genre types for books
	FANTASY("Fantasy"), THRILLER("Thriller"), CRIME("Crime"), LOVE_STORY("Love Story"), SCIENCE_FICTION(
			"Science Fiction"), HORROR("Horror"), HISTORICAL_NOVEL("Historical Novel");

	/**
	 * The genre type.
	 */
	private final String genre;

	/**
	 * Private constructor.
	 * 
	 * @param genre
	 *            Genre type.
	 */
	private GenreType(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the genre.
	 * 
	 * @return Genre type for the book.
	 */
	public String getGenre() {
		return genre;
	}
}
