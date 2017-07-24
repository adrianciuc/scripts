criteria = [
    'livingRoom': [
        'minScore'  : 0,
        'maxScore'  : 100,
        'minValue' : 15,
        'maxValue'  : 30,
        'computeable': true
    ],
    'rooms'     : [
        'minScore'  : 50,
        'maxScore'  : 100,
        'minValues' : [3],
        'maxValues' : [4,5],
        'computeable': false,
        'compute': {v ->
            criteria['rooms']['minValues'].contains(v) ? criteria['rooms']['minScore'] : criteria['rooms']['maxScore']
        }
    ],
    'terrainSurface'   : [
        'minValue' : 200,
        'maxValue' : 600,
        'minScore'  : 0,
        'maxScore'  : 100,
        'computeable': true
    ],
    'price'     : [
        'minValue' : 60000,
        'maxValue' : 90000,
        'minScore'  : 100,
        'maxValues' : 0,
        'computeable': false,
        'compute': { v ->
            100 - computeScoreForProperty("price", v)
        }
    ],
    'fieldAngle': [
        'minScore'  : 20,
        'maxScore'  : 100,
        'computeable': false,
        'compute': { v ->
            v ? criteria['fieldAngle']['minScore'] : criteria['fieldAngle']['maxScore']
        }
    ]
]

def computeScore(house) {
    def scores = house.collect {k, v ->
        ["${k}": criteria[k]['computeable'] ? computeScoreForProperty(k, v) : criteria[k]['compute'](v)]
    }
    print '\n\n\n'
    println '|------------------------------------------------|'
    println '| Here are the scores optained for each criteria |'
    println '|------------------------------------------------|'
    print '\n'
    scores.each {println it}
    print '\n'
    println '|------------------------------------------------|'
    println '|          Here is the over all score            |'
    println '|------------------------------------------------|'
    print '\n'
    println scores.sum {it.values()[0]} / criteria.keySet().size()
    print '\n\n\n'
}

def computeScoreForProperty(k, v) {
    (v - criteria[k]['minValue'])/(criteria[k]['maxValue'] - criteria[k]['minValue']) * 100
}

def house1 = [
    'livingRoom': 27,
    'rooms': 4,
    'terrainSurface': 200,
    'price': 66000,
    'fieldAngle': false
]


computeScore(house1)