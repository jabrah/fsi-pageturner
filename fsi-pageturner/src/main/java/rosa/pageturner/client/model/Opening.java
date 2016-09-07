package rosa.pageturner.client.model;

public class Opening {

    final Page verso;
    final Page recto;

    final String label;
    final int position;

    public Opening(Page verso, Page recto, String label, int position) {
        this.verso = verso;
        this.recto = recto;
        this.label = label;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opening)) return false;

        Opening opening = (Opening) o;

        if (position != opening.position) return false;
        if (verso != null ? !verso.equals(opening.verso) : opening.verso != null) return false;
        if (recto != null ? !recto.equals(opening.recto) : opening.recto != null) return false;
        return label != null ? label.equals(opening.label) : opening.label == null;

    }

    @Override
    public int hashCode() {
        int result = verso != null ? verso.hashCode() : 0;
        result = 31 * result + (recto != null ? recto.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + position;
        return result;
    }
}
