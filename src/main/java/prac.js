function nbYear(p0, percent, aug, p) {
    var no_of_years = 0;
    for (; p0 < p;) {
            p0 = p0 + (p0 * percent / 100) + aug;
            no_of_years++;
        }
        return no_of_years;

}
function towerBuilder(nFloors) {
    var previous=nFloors-1;
    var fibb=previous+nFloors;
    var arr =[]
var fibo=[];
var first=0;
var second=1;
    for(var k=0;k<fibb;k++)
    {

        var third=k+(k+1);
        fibo.push(third)
    }
    var mid=Math.trunc(fibb/2);
    console.log("mid is ",mid)
    for(var p=nFloors;p>0;p--) {
        var str="";
        for(j=p; j<nFloors; j++)
        {
           str+=" ";
        }


        for (var d=1; d>2*p-1; d++) {
            str+="*";

        }

        arr.push(str);
    }


    return arr;
    }




// for(var y = 0; p0 < p; y++) p0 = p0 * (1 + percent / 100) + aug;
// return y;
// console.log(nbYear(1500, 5, 100, 5000));
// console.log(nbYear(1500000, 2.5, 10000, 2000000));
// console.log(nbYear(1500000, 0.25, 1000, 2000000));
// console.log(nbYear(1500000, 0, 10000, 2000000))
//
console.log(towerBuilder(9));
