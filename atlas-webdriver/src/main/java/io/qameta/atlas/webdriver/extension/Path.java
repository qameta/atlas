package io.qameta.atlas.webdriver.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Path marker. Use to replace item defined in your uri path.
 * E.g. if path is "/book/{hotelId}/{roomNumber}" you can do like this:
 *
 * <pre>{@code
 * @Page(url = "book/{hotelId}/{roomNumber}")
 * MainPage onMainPage(@Path("hotelId") long id, @Path("roomNumber") String number);
 * }</pre>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {

    String value();

}
