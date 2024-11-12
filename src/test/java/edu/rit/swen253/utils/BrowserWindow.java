package edu.rit.swen253.utils;

import edu.rit.swen253.page.AbstractPage;

import java.util.Objects;


/**
 * The representation of a single web browser window that includes
 * a Selenium window handle and the Page Object that is currently
 * displayed in this window.
 */
public record BrowserWindow<P extends AbstractPage>(String handle, P page) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BrowserWindow<?> that)) return false;
    return Objects.equals(handle, that.handle);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(handle);
  }
}
