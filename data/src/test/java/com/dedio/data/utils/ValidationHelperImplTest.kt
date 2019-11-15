package com.dedio.data.utils

import org.junit.Assert.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ValidationHelperImplTest : Spek({

    val subject by memoized { ValidationHelperImpl() }

    describe("checkIsNotEmpty(String)") {
        fun callMethod(string: String?) = subject.checkIsNotEmpty(string)

        it("should return false when arg is null") {
            assertFalse(callMethod(null))
        }

        it("should return false when arg is empty") {
            assertFalse(callMethod(""))
        }

        it("should return true when arg is not empty") {
            assertTrue(callMethod("test"))
        }
    }

    describe("checkIsNotEmpty(Collection)") {
        fun callMethod(collection: Collection<*>?) = subject.checkIsNotEmpty(collection)

        it("should return false when arg is null") {
            assertFalse(callMethod(null))
        }

        it("should return false when collection is empty") {
            assertFalse(callMethod(emptyList<Any>()))
        }

        it("should return true when collection is not empty") {
            assertTrue(callMethod(listOf(Any())))
        }
    }

    describe("checkIsNotEmpty(Any)") {
        fun callMethod(obj: Any?) = subject.checkIsNotEmpty(obj)

        it("should return false when arg is null") {
            assertFalse(callMethod(null))
        }

        it("should return true when arg is not null") {
            assertTrue(callMethod(Any()))
        }
    }
})