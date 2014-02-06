/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var p = {y: 11};
function Test() {
  this.x = 10;
}
Test.prototype = p;

function Test2() {
  this.x = 20;
}
Test2.prototype = p;

var test1 = new Test();
console.log(test1.y);
var test2 = new Test2();
console.log(test2.y);
p.y = 30;
console.log(test1.y);

console.log(test2.y);
