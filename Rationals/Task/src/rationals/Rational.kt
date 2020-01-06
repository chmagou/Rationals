package rationals


import java.lang.IllegalArgumentException
import java.math.BigInteger

class Rational(val numerator: BigInteger, val denominator: BigInteger) : Comparable<Rational> {

    operator fun plus(rightRat: Rational) : Rational = ((this.numerator * rightRat.denominator) + (rightRat.numerator * this.denominator)).divBy(this.denominator * rightRat.denominator)
    operator fun minus(rightRat: Rational) : Rational = ((this.numerator * rightRat.denominator) - (rightRat.numerator * this.denominator)).divBy(this.denominator * rightRat.denominator)
    operator fun times(rightRat: Rational) : Rational = ((this.numerator * rightRat.numerator).divBy(this.denominator * rightRat.denominator))
    operator fun div(rightRat: Rational) : Rational =  ((this.numerator * rightRat.denominator).divBy(this.denominator * rightRat.numerator))
    operator fun unaryMinus() : Rational = (this.numerator.negate()).divBy(this.denominator)

    override operator fun compareTo(other: Rational) : Int {

        val mNum = this.numerator * other.denominator;
        val oNum = other.numerator * this.denominator;
        return mNum.compareTo(oNum)

    }

    operator fun rangeTo(end: Rational): ClosedRange<Rational> {
        return object : ClosedRange<Rational> {
            override val endInclusive: Rational = end
            override val start: Rational = this@Rational
        }
    }



    override fun equals(other: Any?): Boolean {

        val normalizedThis = normalized(this)
        val normalizedOther = normalized(other as Rational)
        val thisDivision = (normalizedThis.numerator.toDouble().div(normalizedThis.denominator.toDouble()))
        val otherDivision = (normalizedOther.numerator.toDouble().div(normalizedOther.denominator.toDouble()))

        return (thisDivision == otherDivision)
    }

    override fun toString() : String {

        return when {
            this.denominator == 1.toBigInteger() || this.numerator.rem(this.denominator) == 0.toBigInteger() -> this.numerator.div(this.denominator).toString()
            this.denominator != null -> {
                val normalizedForm = normalized(this)
                if ((denominator < 0.toBigInteger()))
                    "${(normalizedForm.numerator).negate()}/${(normalizedForm.denominator).negate()}"
                else
                    "${normalizedForm.numerator}/${normalizedForm.denominator}"
            }
            else -> "Cannot return String"
        }

    }


}

fun String.toRational() : Rational {

    val number = split("/")


    when {
        number.size == 1 -> return Rational(number[0].toBigInteger(), 1.toBigInteger())
        else -> return Rational(number[0].toBigInteger(), number[1].toBigInteger())
    }

}

fun normalized(num : Rational) : Rational {

    val gcd = (num.numerator).gcd(num.denominator)
    if (gcd != null) {
        val newNum = num.numerator / gcd
        val newDen = num.denominator / gcd
        return Rational(newNum, newDen)
    }

    return num

}

infix fun Int.divBy(den : Int) : Rational = Rational(this.toBigInteger(), den.toBigInteger())
infix fun Long.divBy(den : Long) : Rational = Rational(this.toBigInteger(), den.toBigInteger())
infix fun BigInteger.divBy(den : BigInteger) : Rational = Rational(this, den)

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}


