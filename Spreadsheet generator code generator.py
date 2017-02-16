fields = ['teamNum','teamName','avgAutoScore', 'avgTeleOpScore','avgMatchScore',
          'avgAutoKpa','avgTeleOpGears', 'avgTotalFuel',
          'avgHoppers', 'sdAutoScore', 'sdTeleOpScore',
          'sdMatchScore','sdTeleOpGears','sdTotalFuel',
          'takeoffAttemptPercentage','takeoffSuccessPercentage',
          'pilotPlayPercentage','autoAbility', 'teleOpAbility', 'driveTeamAbility',
          'robotQualities', 'firstPickAbility','secondPickAbility',
          'frequentRobotCommentStr','frequentPilotCommentStr']

header = 'String header = \"'
for field in fields:
    header+= field+','
header+='\\n\";'
fileContents = 'fileContents += '
for field in fields:
    fileContents+='report.'+field+'+COMMA+'

fileContents+='+\'\\n\';'

print(header)
print('\n')
print(fileContents)
