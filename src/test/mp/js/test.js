/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var test = {
  test:{
    y:10,
    z:function(ld){
      console.log(this.y + ld);
    }
  }
};

var what = {
  y:20
};


test.test.z(20);
what.z = test.test.z;
what.z(20);
test.test.z(20);
console.log(what.z == test.test.z);


var zzz = test.test.z;
zzz.y = 1;
console.log(zzz(30));

