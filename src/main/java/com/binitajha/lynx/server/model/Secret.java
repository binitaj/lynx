package com.binitajha.lynx.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Secret implements Serializable {

    public String id;

    public String key;
    public String data;
    public String encrypted;

    public Secret(String i, String k, String d, String e) {
        this.encrypted = e;
        this.id = i;
        this.data = d;
        this.encrypted = e;
    }

    public Secret(Secret other) {
        this.id = other.id;
        this.data = other.data;
        this.key = other.key;
        this.encrypted = other.encrypted;
    }

    @Override
    public String toString() {
        return String.format("[id:(%s) key:(%s) data:(%s) enc: (%s)]", id, key, data, encrypted);
    }
}