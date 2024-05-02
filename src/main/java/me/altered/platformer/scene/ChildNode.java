package me.altered.platformer.scene;

import io.github.humbleui.skija.Canvas;
import kotlin.properties.PropertyDelegateProvider;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import me.altered.platformer.glfw.input.InputEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * NOTE: this class is written in Java purely because of member visibility issues
 */
public class ChildNode<T extends @Nullable Node>
        extends Node
        implements ReadWriteProperty<@NotNull Node, T>,
        PropertyDelegateProvider<@NotNull Node, @NotNull ChildNode<T>> {

    private T node;

    public ChildNode(T node) {
        super();
        this.node = node;
    }

    @NotNull
    @Override
    public String getName() {
        return node != null ? "delegated[" + node.getName() + "]" : super.getName();
    }

    @Nullable
    @Override
    public Node getParent() {
        return node != null ? node.getParent() : null;
    }

    @Override
    public void setParent(@Nullable Node value) {
        if (node != null) node.setParent(value);
    }

    @NotNull
    @Override
    public Set<Node> getChildren() {
        return node != null ? node.getChildren() : Collections.emptySet();
    }

    @Override
    public void addChild(@NotNull Node child) {
        if (node != null) node.addChild(child);
    }

    @Override
    public void removeChild(@NotNull Node child) {
        if (node != null) node.removeChild(child);
    }

    @Override
    protected void ready() {
        if (node != null) node.ready();
    }

    @Override
    protected void update(float delta) {
        if (node != null) node.update(delta);
    }

    @Override
    protected void draw(@NotNull Canvas canvas) {
        if (node != null) node.draw(canvas);
    }

    @Override
    protected void input(@NotNull InputEvent event) {
        if (node != null) node.input(event);
    }

    @Override
    protected void destroy() {
        if (node != null) node.destroy();
    }

    @NotNull
    @Override
    public String toString() {
        return node != null ? "delegated[" + node + "]" : super.toString();
    }

    @Override
    @NotNull
    public ChildNode<T> provideDelegate(@NotNull Node thisRef, @NotNull KProperty<?> prop) {
        if (node != null) {
            node.setParent(thisRef);
        }
        return this;
    }

    @Override
    public T getValue(@NotNull Node thisRef, @NotNull KProperty<?> prop) {
        if (node != null) {
            node.setParent(thisRef);
        }
        return node;
    }

    @Override
    public void setValue(@NotNull Node thisRef, @NotNull KProperty<?> prop, T value) {
        if (node != null) {
            node.setParent(null);
        }
        node = value;
        if (value != null) {
            value.setParent(thisRef);
        }
    }
}
