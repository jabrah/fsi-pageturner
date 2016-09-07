package rosa.pageturner.client.model;

import java.util.List;

public class Book {
    /** Directory in FSI server that represents this book. All page IDs must be prefixed with this value. */
    public final String fsiDirectory;
    public final List<Opening> openings;

    public final String missingImage;

    public Book(String fsiDirectory, List<Opening> openings, String missingImage) {
        if (fsiDirectory == null || fsiDirectory.isEmpty()) {
            throw new IllegalArgumentException("Fsi directory must be specified for this book.");
        }
        if (openings == null || openings.isEmpty()) {
            throw new IllegalArgumentException("List of openings must be provided for this book.");
        }
        this.fsiDirectory = fsiDirectory;
        this.openings = openings;
        this.missingImage = missingImage;
    }

    /**
     * @param pageId desired page ID
     * @return the opening containing pageId, NULL if no opening is found.
     * @throws IllegalArgumentException if no pageId is specified
     */
    public Opening getOpening(String pageId) {
        if (pageId == null || pageId.isEmpty()) {
            throw new IllegalArgumentException("Page ID must be specified.");
        }

        for (Opening opening : openings) {
            if ((opening.verso != null && opening.verso.id.equals(pageId)) ||
                    (opening.recto != null && opening.recto.id.equals(pageId))) {
                return opening;
            }
        }

        return null;
    }

    public Opening getOpening(int index) {
        if (index < 0 || index >= openings.size()) {
            throw new IndexOutOfBoundsException();
        }
        return openings.get(index);
    }

    /**
     * Get a single string containing all page IDs separated by commas. This list
     * preserves the ordering of the images.
     *
     * @return a comma separated list of all page IDs
     */
    public String getPagesList() {
        StringBuilder sb = new StringBuilder();

        for (Opening current : openings) {
            if (current.verso != null && !current.verso.missing) {
                sb.append(current.verso.id).append(',');
            }
            if (current.recto != null && !current.recto.missing) {
                sb.append(current.recto.id).append(',');
            }
        }

        return sb.deleteCharAt(sb.length()).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (fsiDirectory != null ? !fsiDirectory.equals(book.fsiDirectory) : book.fsiDirectory != null) return false;
        return openings != null ? openings.equals(book.openings) : book.openings == null;

    }

    @Override
    public int hashCode() {
        int result = fsiDirectory != null ? fsiDirectory.hashCode() : 0;
        result = 31 * result + (openings != null ? openings.hashCode() : 0);
        return result;
    }
}
