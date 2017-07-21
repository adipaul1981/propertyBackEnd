package otherscope {

  /**
    * Created by Adi on 2017-03-15.
    */
  class foo {
    def exec(f:(String) => Unit, name: String) ={
      f(name)
    }
  }

}

object ClosureExample extends App{
  var hello = "Hello"
  def sayHello(name: String){println(s"$hello, $name")}

  val foo = new otherscope.foo
  foo.exec(sayHello,"Al")

  hello = "Hola"
  foo.exec(sayHello, "Lorenzo")

  import scala.collection.mutable.ArrayBuffer

  val fruits = ArrayBuffer("apple")

  val addToBasket = (s:String) => {
    fruits += s
    println(fruits.mkString(", "))
  }

  def buyStuff(f:String => Unit, s:String): Unit = {
    f(s)
  }

  println(buyStuff(addToBasket, "Cherries"))
  println(buyStuff(addToBasket, "grapes"))

  val devide = new PartialFunction[Int, Int] {
    def apply(x:Int) = 42/x

    def isDefinedAt(x: Int) = x != 0  }

}

