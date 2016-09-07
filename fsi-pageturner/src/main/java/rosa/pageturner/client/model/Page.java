package rosa.pageturner.client.model;

public class Page {
    final String id;
    final String label;
    final boolean missing;

    final int width;
    final int height;

    public Page(String id, String label, boolean missing, int width, int height) {
        if (id == null) {
            throw new IllegalArgumentException("ID must be specified.");
        }
        this.id = id;
        this.label = label;
        this.missing = missing;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;

        Page page = (Page) o;

        if (missing != page.missing) return false;
        if (width != page.width) return false;
        if (height != page.height) return false;
        if (!id.equals(page.id)) return false;
        return label != null ? label.equals(page.label) : page.label == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (missing ? 1 : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
