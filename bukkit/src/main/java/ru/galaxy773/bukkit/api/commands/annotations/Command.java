package ru.galaxy773.bukkit.api.commands.annotations;

import ru.galaxy773.multiplatform.api.gamer.constants.Group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    String name();

    String[] aliases();

    int minArgs() default 0;

    int maxArgs() default Integer.MAX_VALUE;

    boolean onlyConsole() default false;

    boolean onlyPlayers() default false;

    boolean onlyGroup() default false;

    Group group() default Group.DEFAULT;

    int cooldown() default 0;
}
