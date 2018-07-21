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
        if(value){
            bits |= mask.bits;
        }else {
            if(bits == 0){
                return;
            }
            bits ^= mask.bits;
        }
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
//       no tests for it
    }

    public boolean any() {
        return bits != 0L;
    }

    public boolean anyOf(Bitfield other) {
        return (bits & other.bits) != 0L;
    }

    public boolean all() {
        return bits == ~0L;
    }

    public boolean allOf(Bitfield other) {
        return (bits & other.bits) == other.bits;
    }

    public void invert() {
        bits = ~bits;
    }

    public void keep(Bitfield other) {
        bits &= other.bits;
    }

    public void include(Bitfield other) {
        bits |= other.bits;
    }

    public Bitfield not() {
        Bitfield result = new Bitfield();
        result.bits = ~ bits;

        return result;
    }

    public Bitfield and(Bitfield other) {
        Bitfield result = new Bitfield(this);
        result.bits = bits & other.bits;

        return result;
    }

    public Bitfield or(Bitfield other) {
        Bitfield result = new Bitfield(this);
        result.bits = bits | other.bits;

        return result;
    }

    public Bitfield xor(Bitfield other) {
        Bitfield result = new Bitfield(this);
        result.bits = bits ^ other.bits;

        return result;
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
