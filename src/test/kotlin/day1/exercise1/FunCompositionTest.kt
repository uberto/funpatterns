package day1.exercise1

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class FunCompositionTest {

    fun inc(x: Int) = x + 1
    fun double(x: Int) = x * 2
    fun pad(s: String) = " $s"
    fun mirror(s: String) = "$s${s.reversed()}"
    fun strLen(s:String): Int = s.length


    @Test
    fun `f on g  is equivalent to f(g())`(){

        val r = inc(double(5)) //11

        val newFun = ::inc on ::double

        assertThat(newFun(5)).isEqualTo(r)

    }

    @Test
    fun `f on g with differ5nt types  is equivalent to f(g())`(){

        val r = inc(strLen("ciao")) //5

        val newFun = ::inc on ::strLen

        assertThat(newFun("ciao")).isEqualTo(r)

    }


    @Test
    fun `f andThen g  is equivalent to g(f())`(){

        val r = mirror(pad("*")) //" ** "

        val newFun = ::pad andThen ::mirror

        assertThat(newFun("*")).isEqualTo(r)

    }
}