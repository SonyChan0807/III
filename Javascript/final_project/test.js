const child = require('async-child-process');

async function test() {

  const stdout = await child.execAsync('Rscript ~/Desktop/final_proejct/III/Javascript/final_project/treetest.R 2015 BMW 80000');
  console.log(stdout);
}
test()