package groovy.json

import static groovy.json.JsonOutput.toJson

/**
 * 
 * @author Guillaume Laforge
 */
class JsonOutputTest extends GroovyTestCase {
    
    void testBooleanValues() {
        assert toJson(Boolean.TRUE) == "true"
        assert toJson(Boolean.FALSE) == "false"
        assert toJson(true) == "true"
        assert toJson(false) == "false"
    }

    void testNullValue() {
        assert toJson(null) == "null"
    }

    void testNumbers() {
        assert toJson(-1) == "-1"
        assert toJson(1) == "1"
        assert toJson(0) == "0"
        assert toJson(100) == "100"
        assert toJson(100) == "100"

        assert toJson((short)100) == "100"
        assert toJson((byte)100) == "100"

        // Long
        assert toJson(1000000000000000000) == "1000000000000000000"
        // BigInteger
        assert toJson(1000000000000000000000000) == "1000000000000000000000000"

        // BigDecimal
        assert toJson(0.0) == "0.0"
        assert toJson(0.0) == "0.0"

        // Double
        assert toJson(Math.PI) == "3.141592653589793"
        // Float
        assert toJson(1.2345f) == "1.2345"

        // exponant
        assert toJson(1234.1234e12) == "1.2341234E+15"

        shouldFail { toJson(Double.NaN) }
        shouldFail { toJson(Double.POSITIVE_INFINITY) }
        shouldFail { toJson(Double.NEGATIVE_INFINITY) }
        shouldFail { toJson(Float.NaN) }
        shouldFail { toJson(Float.POSITIVE_INFINITY) }
        shouldFail { toJson(Float.NEGATIVE_INFINITY) }
    }

    void testEmptyListOrArray() {
        assert toJson([]) == "[]"
        assert toJson([] as Object[]) == "[]"
    }

    void testListOfPrimitives() {
        assert toJson([true, false, null, true, 4, 1.1234]) == "[true,false,null,true,4,1.1234]"
        assert toJson([true, [false, null], true, [4, [1.1234]]]) == "[true,[false,null],true,[4,[1.1234]]]"
    }

    void testPrimitiveArray() {
        assert toJson([1, 2, 3, 4] as byte[]) == "[1,2,3,4]"
        assert toJson([1, 2, 3, 4] as short[]) == "[1,2,3,4]"
        assert toJson([1, 2, 3, 4] as int[]) == "[1,2,3,4]"
        assert toJson([1, 2, 3, 4] as long[]) == "[1,2,3,4]"
    }

    void testEmptyMap() {
        assert toJson([:]) == "{}"
    }

    void testMap() {
        assert toJson([a: 1]) == '{"a":1}'
        assert toJson([a: 1, b: 2]) == '{"a":1,"b":2}'
        assert toJson([a: 1, b: true, c: null, d: [], e: 'hello']) == '{"a":1,"b":true,"c":null,"d":[],"e":"hello"}'
    }

    void testString() {
        assert toJson("") == '""'

        assert toJson("a") == '"a"'
        assert toJson("abcdef") == '"abcdef"'

        assert toJson("\b") == '"\\b"'
        assert toJson("\f") == '"\\f"'
        assert toJson("\n") == '"\\n"'
        assert toJson("\r") == '"\\r"'
        assert toJson("\t") == '"\\t"'

        assert toJson('"') == '"\\""'
        assert toJson("/") == '"\\/"'
        assert toJson("\\") == '"\\\\"'

        assert toJson("\u0001") == '"\\u0001"' 
        assert toJson("\u0002") == '"\\u0002"'
        assert toJson("\u0003") == '"\\u0003"'
        assert toJson("\u0004") == '"\\u0004"'
        assert toJson("\u0005") == '"\\u0005"'
        assert toJson("\u0006") == '"\\u0006"'
        assert toJson("\u0007") == '"\\u0007"'
        assert toJson("\u0010") == '"\\u0010"'
        assert toJson("\u0011") == '"\\u0011"'
        assert toJson("\u0012") == '"\\u0012"'
        assert toJson("\u0013") == '"\\u0013"'
        assert toJson("\u0014") == '"\\u0014"'
        assert toJson("\u0015") == '"\\u0015"'
        assert toJson("\u0016") == '"\\u0016"'
        assert toJson("\u0017") == '"\\u0017"'
        assert toJson("\u0018") == '"\\u0018"'
        assert toJson("\u0019") == '"\\u0019"'
    }

    void testComplexObject() {
        assert toJson([name: 'Guillaume', age: 33, address: [line1: "1 main street", line2: "", zip: 1234], pets: ['dog', 'cat']]) ==
                '{"name":"Guillaume","age":33,"address":{"line1":"1 main street","line2":"","zip":1234},"pets":["dog","cat"]}'

        assert toJson([[:],[:]]) == '[{},{}]'
    }

    void testClosure() {
        assert toJson({
            a 1
            b {
                c 2
                d {
                    e 3, {
                        f 4
                    }
                }
            }
        }) == '{"a":1,"b":{"c":2,"d":{"e":[3,{"f":4}]}}}'
    }

    void testIteratorEnumeration() {
        assert toJson([1, 2, 3].iterator()) == '[1,2,3]'
        assert toJson(Collections.enumeration([1, 2, 3])) == '[1,2,3]'

    }
}