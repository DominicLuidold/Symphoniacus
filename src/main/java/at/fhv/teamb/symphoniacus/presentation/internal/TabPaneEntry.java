package at.fhv.teamb.symphoniacus.presentation.internal;

import java.util.Objects;

/**
 * A Tab Pane entry which is mainly needed for
 * {@link at.fhv.teamb.symphoniacus.presentation.MainController}
 * to build the Tab Pane Header menu.
 *
 * @author Valentin
 */
public class TabPaneEntry {
    private int order;
    private String title;
    private String fxmlPath;

    /**
     * Constructs a new TabPaneEntry.
     *
     * @param order    The order in which this FXML should be loaded (1 = first)
     * @param title    Title of Tab
     * @param fxmlPath Path to FXML file including /view/ as prefix
     */
    public TabPaneEntry(int order, String title, String fxmlPath) {
        this.order = order;
        this.title = title;
        this.fxmlPath = fxmlPath;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TabPaneEntry that = (TabPaneEntry) o;
        return order == that.order
            && Objects.equals(title, that.title)
            && Objects.equals(fxmlPath, that.fxmlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, title, fxmlPath);
    }
}
