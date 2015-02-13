// Copyright (c) 2011-2015 ScalaMock Contributors (https://github.com/paulbutcher/ScalaMock/graphs/contributors)
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package org.scalamock.clazz

import org.scalamock.context.MockContext
import org.scalamock.function._
import org.scalamock.util.Defaultable

object MockImpl {
  import scala.reflect.macros.whitebox.Context

  def mock[T: c.WeakTypeTag](c: Context)(mockContext: c.Expr[MockContext]): c.Tree = {
    val maker = MockMaker[T](c)(mockContext, stub = false, mockName = None)
    maker.make
  }

  def stub[T: c.WeakTypeTag](c: Context)(mockContext: c.Expr[MockContext]): c.Tree = {
    val maker = MockMaker[T](c)(mockContext, stub = true, mockName = None)
    maker.make
  }

  def mockWithName[T: c.WeakTypeTag](c: Context)(mockName: c.Expr[String])(mockContext: c.Expr[MockContext]): c.Tree = {
    val maker = MockMaker[T](c)(mockContext, stub = false, mockName = Some(mockName))
    maker.make
  }

  def stubWithName[T: c.WeakTypeTag](c: Context)(mockName: c.Expr[String])(mockContext: c.Expr[MockContext]): c.Tree = {
    val maker = MockMaker[T](c)(mockContext, stub = true, mockName = Some(mockName))
    maker.make
  }

  def MockMaker[T: c.WeakTypeTag](c: Context)(mockContext: c.Expr[MockContext], stub: Boolean, mockName: Option[c.Expr[String]]) = {
    val m = new MockMaker[c.type](c)
    new m.MockMakerInner[T](mockContext, stub, mockName)
  }
}
