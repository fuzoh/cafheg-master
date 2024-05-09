package ch.hearc.cafheg.business.allocations;

import java.util.Objects;

public class NoAVS {

  public String value;

  public NoAVS() {
  }

  public NoAVS(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    NoAVS noAVS = (NoAVS) o;
    return Objects.equals(getValue(), noAVS.getValue());
  }
}
