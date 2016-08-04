package net.andwy.andwyadmin.entity;
public class KeyValue<T1, T2> {
    public T1 key;
    public T2 value;
    public KeyValue() {};
    public KeyValue(T1 key, T2 value) {
        this.key = key;
        this.value = value;
    };
}
