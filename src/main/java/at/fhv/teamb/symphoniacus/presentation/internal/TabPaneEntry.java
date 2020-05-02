package at.fhv.teamb.symphoniacus.presentation.internal;

import java.util.Objects;

/**
 * @author Valentin
 */
public class TabPaneEntry {
    private int order;
    private String title;
    private String fxmlPath;

    public TabPaneEntry(int order, String title, String fxmlPath) {
        this.order = 1;
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
        return order == that.order &&
            Objects.equals(title, that.title) &&
            Objects.equals(fxmlPath, that.fxmlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, title, fxmlPath);
    }
}
