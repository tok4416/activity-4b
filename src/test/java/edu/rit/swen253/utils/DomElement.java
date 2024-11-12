package edu.rit.swen253.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


/**
 * A decorator of the Selenium {@link WebElement} objects.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class DomElement {
  /**
   * A Selenium {@linkplain By finder} that returns the current, relative element.
   * This finder cannot be used just with the Driver but rather on an Element.
   */
  public static final By IDENTITY_FINDER = By.xpath(".");

  /**
   * A Selenium {@linkplain By finder} that returns the parent of the current element.
   * This finder cannot be used just with the Driver but rather on an Element.
   */
  private static final By PARENT_FINDER = By.xpath("..");

  //
  // Inner Types
  //

  /**
   * The contract for {@linkplain FluentWait#until(java.util.function.Function) }
   */
  public interface WebElementCondition extends Function<WebElement, Boolean> {}

  //
  // Static factory method
  //

  /**
   * Find a {@linkplain DomElement DOM element} using the Selenium driver
   * and a {@linkplain By location description}.
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The {@link DomElement} created for this location.
   *
   * @throws RuntimeException
   *   when the driver fails to find an element at the location
   */
  public static DomElement findBy(final By by) {
    return new DomElement(SeleniumUtils.getShortWait().until(driver -> driver.findElement(by)));
  }

  /**
   * Find all {@linkplain DomElement DOM elements} using the Selenium driver
   * and a {@linkplain By finder method}.
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The list of {@link DomElement} objects created for this finder.
   */
  public static List<DomElement> findAllBy(final By by) {
    return SeleniumUtils.getShortWait()
        .until(driver -> driver.findElements(by))
        .stream()
        .map(DomElement::new)
        .toList();
  }

  //
  // Attributes
  //

  /**
   * The delegate {@link WebElement} that this DOM element class wraps.
   */
  private final WebElement element;

  //
  // Constructor
  //

  private DomElement(final WebElement element) {
    this.element = element;
  }

  //
  // Public methods
  //

  /**
   * Get the wrapped Selenium {@link  WebElement}.  This allows client code to execute any
   * sort of action directly on the element; by-passing this decorator.
   */
  public WebElement getWebElement() {
    return element;
  }

  /**
   * Get all the visible text inside of this DOM element.
   * <p>
   *   This includes text in child elements.
   * </p>
   */
  public String getText() {
    if (text == null) {
      text = _getText();
    }
    return text;
  }
  private String text;

  /**
   * Get the full text of this element but trimmed and extraneous whitespace removed.
   */
  public String getTrimmedText() {
    return getText().trim().replaceAll("\\s+", " ");
  }

  /**
   * Query this DOM element to see if the supplied {@linkplain By finder} finds a sub-element.
   *
   * @param finder  the {@link By} finder object
   *
   * @return  true if a DOM element for this finder was found; otherwise, false
   */
  public boolean hasChild(final By finder) {
    try {
      findChildBy(finder);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Find a child {@linkplain DomElement DOM element} of this parent DOM element.
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The {@link DomElement} created for this location.
   *
   * @throws RuntimeException
   *   when the driver fails to find an element at the location
   */
  public DomElement findChildBy(By by) {
    return new DomElement(SeleniumUtils.getShortWait().until(driver -> element.findElement(by)));
  }

  /**
   * Find a child {@linkplain DomElement DOM element} of this parent DOM element
   * by piercing the Shadow DOM.
   *
   * <p>
   *   This is used for Firefox tests.
   * </p>
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The {@link DomElement} created for this location.
   *
   * @throws RuntimeException
   *   when the driver fails to find an element at the location
   */
  public DomElement findChildInShadowDom(By by) {
    return new DomElement(SeleniumUtils.getShortWait().until(driver -> element.getShadowRoot().findElement(by)));
  }

  /**
   * Find all child {@linkplain DomElement DOM elements} of this parent DOM element.
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The list of {@link DomElement} created for this location.
   *
   * @throws RuntimeException
   *   when the driver fails to find an element at the location
   */
  public List<DomElement> findChildrenBy(By by) {
    return SeleniumUtils.getMediumWait()
      .until(driver -> element.findElements(by))
      .stream()
      .map(DomElement::new)
      .toList();
  }


  /**
   * Find all child {@linkplain DomElement DOM elements} of this parent DOM element
   * by piercing the Shadow DOM.
   *
   * <p>
   *   This is used for Firefox tests.
   * </p>
   *
   * @param by
   *   the location reference; an implementation of the {@link By} interface
   *
   * @return The list of {@link DomElement} created for this location.
   *
   * @throws RuntimeException
   *   when the driver fails to find an element at the location
   */
  public List<DomElement> findChildrenInShadowDom(By by) {
    return SeleniumUtils.getShortWait()
      .until(driver -> element.getShadowRoot().findElements(by))
      .stream()
      .map(DomElement::new)
      .toList();
  }

  /**
   * Retrieves the parent element.
   *
   * @return The parent of the child.
   */
  public DomElement getParent() {
    // find the parent by looking for a "child", LOL
    return findChildBy(PARENT_FINDER);
  }

  /**
   * Get the value of the element's attribute.
   *
   * @param attributeName  the name of the attribute
   * @return the value of the attribute or null if not found
   */
  public String getAttribute(String attributeName) {
    return element.getAttribute(attributeName);
  }

  /**
   * Get all HTML classes on this DOM element.
   */
  public List<String> getClasses() {
    return Arrays.asList(element.getAttribute(HtmlUtils.CLASS_ATTR).split(" "));
  }

  /**
   * Queries if this element has the given CSS class.
   */
  public boolean hasClass(final String cssClass) {
    return getClasses().contains(cssClass);
  }

  /**
   * Query if this input element is enabled or not.
   *
   * @return  true if the element is enabled
   */
  public boolean isEnabled() {
    return element.isEnabled();
  }

  /**
   * Get the value in a text field.
   */
  public String getInputValue(){
    return getAttribute(HtmlUtils.VALUE_ATTR);
  }

  /**
   * Enters a text in text field.
   */
  public void enterText(CharSequence... var1) {
    element.sendKeys(var1);
  }

  /**
   * Clears the content of text field.
   */
  public void clear() {
    element.clear();
  }

  /**
   * Submit a form.  This element must be a {@code <form>} or input element.
   */
  public void submit() {
    element.submit();
  }

  /**
   * Puts focus on a text field.
   */
  public void focus() {
    SeleniumUtils.executeScript("arguments[0].focus(); return true", this.element);
  }

  /**
   * Blurs away from a text field.
   */
  public void blur() {
    SeleniumUtils.executeScript("arguments[0].blur(); return true", this.element);
  }

  /**
   * Perform a click on this DOM element.
   *
   * @param wait  The timer/wait object to invoke the conditions
   * @param conditionFactories  The collection of {@link ExpectedCondition} factories
   *
   * @throws RuntimeException
   *   when the web element reference goes stale
   */
  public void click(final FluentWait<WebDriver> wait, final WebElementConditionFactory... conditionFactories) {
    Arrays.stream(conditionFactories)
      .map(factory -> factory.apply(element))
      .forEach(wait::until);
    element.click();
  }

  /**
   * Perform a click on this DOM element.
   * <p>
   * This action uses a short wait with the condition that the element is clickable.
   *
   * @throws RuntimeException
   *   when the web element reference goes stale
   */
  public void click() {
    click(SeleniumUtils.getShortWait(), ExpectedConditions::elementToBeClickable);
  }

  /**
   * Scroll the browser page to make this DOM element visible.
   */
  public void scrollIntoView() {
    SeleniumUtils.makeAction().scrollToElement(element).perform();
  }

  /**
   * Wait until this element has vanished from the page.
   */
  public void waitUntilGone() {
    waitUntilGone(SeleniumUtils.getShortWait());
  }

  /**
   * Wait until this element has vanished from the page.
   */
  public void waitUntilGone(final FluentWait<WebDriver> wait) {
    wait.until(ExpectedConditions.stalenessOf(element));
  }

  /**
   * Waits to see if this element will vanish from the page.
   *
   * @return {@code true} if the element vanishes as expected; otherwise {@code false}
   */
  public boolean doesLeave() {
    try {
      waitUntilGone();
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  //
  // Object methods
  //

  @Override
  public String toString() {
    return element.toString();
  }

  //
  // Private methods
  //

  /**
   * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
   * without any leading or trailing whitespace.
   *
   * @return The innerText of this element.
   */
  private String _getText() {
    return element.getText();
  }

  /**
   * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
   * without any leading or trailing whitespace.
   *
   * @return The innerText of this element.
   */
  private String _getInnerText() {
    String text = element.getText().trim();
    List<WebElement> children = element.findElements(By.xpath("./*"));
    for (WebElement child : children) {
      text = text.replaceFirst(child.getText(), "").trim();
    }
    return text;
  }
}
