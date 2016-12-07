'use strict';
/*jshint node:true*/

const fs = require('fs');

// booleans are bytes are intentionally omitted, since they contain too little data
const primitives = [
  "short", "int", "long", "float", "double", "char", 
];

const capPrimitives = [
  "Short", "Int", "Long", "Float", "Double", "Char", 
];

const wrapPrimitives = [
  "Short", "Integer", "Long", "Float", "Double", "Character", 
];

const files = fs.readdirSync('./generators/');

for (const file of files) {
  const str = fs.readFileSync('./generators/' + file, 'utf8');

  const len = primitives.length;

  for (let i = 0; i < len; i++) {
    let s = str.replace(/\$primitive\$/g, primitives[i])
               .replace(/\$primitiveWrp\$/g, wrapPrimitives[i])
               .replace(/\$primitiveFmt\$/g, capPrimitives[i]);

    let f = file.replace(/\$primitive\$/g, primitives[i])
                .replace(/\$primitiveWrp\$/g, wrapPrimitives[i])
                .replace(/\$primitiveFmt\$/g, capPrimitives[i]);

    fs.writeFileSync('./src/main/java/club/bonerbrew/neatarrays/' + f, s);
  }

}