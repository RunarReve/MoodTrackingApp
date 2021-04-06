from numpy import random

#=============================SETUP==========================
min = 0
max = 8

listOfTitles = ["Happiness","Sleep","Movement","Social"] #List of Want and Needs
middleList = [-1, 2, 6, 3] #The middle of standard deviation (-1 means it is want and is based on the needs)
instances = 10 #Number of independent instances
listOfInstances = [[]]
listFunction = [ #Function for each want (f(x) = [0]*x + [1]
			[	#Happiness
			[1, 2],		#Sleep
			[2, -0.5],	#Movement
			[2.5, -2]	#Social
		]
    ] 


for i in range(0,instances-1):#Set up list
	listOfInstances.append([])

#=============================FUNCTIONS==========================

#Calculate based with the various functions
def calculate(x, m, n):
	value = round((x*m) + n)
	#print("f(x) = " + str(x)+ "*"+ str(m) + " + " + str(n) + " = " + str(value)) #DEBUG
	return value

#Cut the value if it becoms out of the scope
def cutValue(input):
	if input < min:
		return min
	if input > max:
		return max
	return input

#Add some randomized noice
def noice(input):
	rand = random.randint(0, 3) # Increase this to decrees the possibility of noice
	if ( rand== 0):
		return cutValue(input + 1)
	if (rand == 1):
		return cutValue(input - 1)
	return cutValue(input)

#Generates a list of Natural numbers with a distrebution focused on a set middle number
def generate(middle, numb):
    choiceList = []
    for i in range(min, max): #Every number has a chance
        choiceList.append(i)
    
	#========Add=more=likelihood=the=closer=to=set=middle=number========
    #Add frequancy of values below middle
    add = 8
    for i in range(middle, min-1, -1):
        for o in range(0, add):
            choiceList.append(i)
        add = add / 2
        if(add < 1 ):
            continue

    #Add frequancy of values above middle
    add = 8/2
    for i in range(middle+1, max, 1):
        for o in range(0, add):
            choiceList.append(i)
        add = add / 2
        if(add < 1 ):
            continue
	#========Add=more=likelihood=the=closer=to=set=middle=number========
    
	#Add some randomnes
    for i in range(0, 3):
        choiceList.append(random.randint(min, max))

    newList = random.choice(choiceList, size=(numb)) 
    print("Average for "+str(middle)+" is: "+str(newList.sum()/float(len(newList))))
    return newList

#=============================FUNCTIONS==========================


#=============================GENERATE=NEEDS==========================

for i in range(0, len(listOfTitles)):
	numb = 0
	if(middleList[i] < 0 ): #Wants are not directaly randome
		while numb < instances: #Set all values for this to ..
			listOfInstances[numb].append(-1) #..0 
			numb += 1
		continue
	for each in generate(middleList[i],instances): #Add all values from randomly generated list
		listOfInstances[numb].append(each)
		numb += 1

#=============================GENERATE=WANTS==========================

for input in range(0, len(listOfInstances)): #For each lists in value
	doneWants = 0
	for i in range(0, len(listOfTitles)): # For each value in the list
		if listOfInstances[input][i] < 0: # If this is a want 
			sum = 0 
			added = 0 
			for j in range(0, len(listOfTitles)):	#Compute against all other ...
				if  listOfInstances[input][j] >= 0:			#... non-wants
					sum += calculate(listOfInstances[input][j], 
							listFunction[doneWants][j-1][0], 
							listFunction[doneWants][j-1][1]) #Calculate what the want will be and add it to sum
					added += 1
			listOfInstances[input][i] = noice(int(sum/added))
			doneWants += 1

#=============================OUT=WRITE==========================

outFile = open ("Data4Fab.kt", 'w')#OutFile

outFile.write("package com.rever.moodtrack.data.Fabricated\n\nobject Data4Fab{\n\tval listO = listOf(")
for i in range(0, instances):
	outString = "\n\t\tlistOf("
	for j in range(0,len(listOfTitles)):
		outString += str(listOfInstances[i][j])
		if(j < len(listOfTitles)-1):
			outString += ", "
		else:
			outString += ")"
	if(i <= instances-2):	#Comma on all except last
		outString += ","
	outFile.write(outString)
outFile.write("\n\t)\n}")
outFile.close()
print("Move generated file to application package: com.rever.moodtrack.data.Fabricated")
