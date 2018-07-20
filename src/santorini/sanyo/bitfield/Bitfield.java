package santorini.sanyo.bitfield;


import java.util.Objects;

public final class Bitfield {
    public static int SIZE = Long.SIZE;

    private long bits = 0;


    public Bitfield() {
    }

    public Bitfield(Bitfield bf) {
        bits = bf.bits;
    }


    private void checkIndex(int index) {
        if(index < 0 || index >= SIZE) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + SIZE);
        }
    }

    public boolean get(int index) {
        checkIndex(index);
        return ((bits  >> index) & 1) == 1;
    }

    public void set(int index, boolean value) {
        checkIndex(index);

        if(value){
            bits |= (1L << index);
        }else{
            bits &= ~(1L << index);
        }
    }

    public void set(Bitfield mask, boolean value) {
        if (value){
            bits = mask.bits;
        }else{
            bits = 0;
        }
//        have to finalize it
    }

    public void set(Bitfield other) {
        bits = other.bits;
    }

    public void setAll(boolean value) {
        if(value){
            bits = ~0L;
        }else{
            bits = 0L;
        }
    }

    public void switchBit(int index) {
        checkIndex(index);

        if(get(index)){
            set(index, false);
        }else{
            set(index, true);
        }
    }

    public void switchBits(Bitfield mask) {
        bits ^= mask.bits;
    }

    private void switchBits(long mask) {
    }

    public boolean any() {
        return false;
    }

    public boolean anyOf(Bitfield other) {
        return false;
    }

    public boolean all() {
        return false;
    }

    public boolean allOf(Bitfield other) {
        return false;
    }

    public void invert() {
    }

    public void keep(Bitfield other) {
    }

    public void include(Bitfield other) {
    }

    public Bitfield not() {
        return new Bitfield();
    }

    public Bitfield and(Bitfield other) {
        return new Bitfield();
    }

    public Bitfield or(Bitfield other) {
        return new Bitfield();
    }

    public Bitfield xor(Bitfield other) {
        return new Bitfield();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitfield bitfield = (Bitfield) o;
        return bits == bitfield.bits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bits);
    }

    @Override
    public String toString() {
        return Long.toBinaryString(bits);
    }
}
