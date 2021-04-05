from numpy import random

topVal = 8
botVal = 0

def generate(middle, numb):
    choiceList = []
    for i in range(botVal, topVal): #Everything has a chance
        choiceList.append(i)
    
    #Add frequancy of values below middle
    add = 8
    for i in range(middle, botVal-1, -1):
        for o in range(0, add):
            choiceList.append(i)
        add = add / 2
        if(add < 1 ):
            continue

    #Add frequancy of values above middle
    add = 8/2
    for i in range(middle+1, topVal, 1):
        for o in range(0, add):
            choiceList.append(i)
        add = add / 2
        if(add < 1 ):
            continue

    #Add some randomnes
    for i in range(0, 3):
        choiceList.append(random.randint(botVal, topVal))

    newList = random.choice(choiceList, size=(numb))
    #print(choiceList)
    print("Average for "+str(middle)+" is: "+str(newList.sum()/float(len(newList))))
    return newList


#Preset list of varables and the middle of deviancy of it
listOfTitles = ["Happiness","Sleep","Movement","Social"]
middleList = [5, 2, 6, 3]
listOfInstances = [[]]
instances = 10

for each in range(0, len(listOfTitles)): 
    for beach in generate(middleList[each],instances):
        listOfInstances[-1].append(beach)
    listOfInstances.append([])

listOfInstances.remove([])

#Set up file 
f = open ("Data4Fab.kt", 'w')
#Stupid method, but works

f.write("package com.rever.moodtrack.data.Fabricated\n\nobject Data4Fab{\n\tval listO = listOf(")
for i in range(0, instances):
    f.write("\n\t\tlistOf("
            +str(listOfInstances[0][i])+", "
            +str(listOfInstances[1][i])+", "
            +str(listOfInstances[2][i])+", "
            +str(listOfInstances[3][i])+")")    
    if(i <= instances-2):#Comma on all except last
        f.write(",")
        
f.write("\n\t)\n}")
f.close()
print("Move generated file to application package: com.rever.moodtrack.data.Fabricated")
