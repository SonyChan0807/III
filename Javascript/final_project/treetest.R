require(utils)
library(stats)
library(mlbench)
library(lattice)
library(Cubist)

#輸入值為:2015 BMW 19600 (共3位)以空格格開 / 為years.brand(大寫),里程數
argV <- commandArgs(TRUE)
anadata<-c(argV)
anadata<-data.frame(years=c(anadata[1]),brand=c(anadata[2]),mile=c(anadata[3]))

#setwd("C:\\Program Files\\R\\R-3.4.0patched\\bin")

modelTree = readRDS('~/Desktop/final_proejct/III/Javascript/final_project/treetest.octet-stream', refhook = FALSE)
mtPred <- predict(modelTree, anadata)

print (mtPred[1])