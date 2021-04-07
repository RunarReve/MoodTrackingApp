import pyrebase
import sys
import json


#===========Initializing connection to Firebase================
firebaseConfig={
    'apiKey': "AIzaSyAQx31yXU9Iuzy0rsJO40eWJ98i6MMsJiU",
    'authDomain': "need2want.firebaseapp.com",
    'databaseURL': "https://need2want-default-rtdb.europe-west1.firebasedatabase.app",
    'projectId': "need2want",
    'storageBucket': "need2want.appspot.com",
    'messagingSenderId': "214189934637",
    'appId': "1:214189934637:web:a28437adf7dd580af290a4",
    'measurementId': "G-CLV47SKJFC"}

firebase = pyrebase.initialize_app(firebaseConfig)
auth = firebase.auth()


#===========Authorizing access to the Firebase as admin, and only as admin==============
print("To authorize you are allowed to download data of participants")
email    = input("Enter an admin email: ")
password = input("Enter password: ")

try:    #Check it is formatted correctly
    if email.split('@')[1].split('.')[0] != 'admin':
        sys.exit("Not an admin account\nWill close now")
except:
    sys.exit("Values do not match")  

try: #Try to log the user in
    currentUser = auth.sign_in_with_email_and_password(email, password)
except:
    sys.exit("Account not found")
    
print("Access Granted")
s = ',' #seperator

#Setup files and header of the files
userInfoList = ['gender', 'ethnicity', 'nationality', 'postCode'] #Wanted information about each user
userInfo = open("userInfo.csv", 'w') #Store the corresponding information about each available user
userInfo.write("UserID" +s+ s.join(userInfoList))

defaultNeeds = ['Happiness', 'Movement', 'Sleep', 'Social'] #Wants and needs gathering
dataInfo = open("dataInfo.csv", 'w') #Store every inputs of the default wants and needs
dataInfo.write("UserID"+s+"InputNumb"+s+s.join(userInfoList)) #Default

users = firebase.database().child("user").get(currentUser['idToken'])
for user in users: #For each available user
    #Get information about the user
    info = user.val()['userInfo']
    currentUserID = info['userId'] #TODO Could hash the user ID to get another layer of security

    string = currentUserID
    for each in userInfoList:
        string += s + info[each]
    userInfo.write('\n' + string)

    #Get datapoints of the user 
    data = user.val()['data']
    for inputs in data:
        string = currentUserID +s+ inputs
        for each in defaultNeeds:
            string += s + str(data[inputs][each]['rate'])
        dataInfo.write('\n' + string)

#Close writing
userInfo.close()
dataInfo.close()