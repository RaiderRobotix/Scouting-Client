fields = ['teamNum','teamName','avgAutoScore', 'avgTeleOpScore','avgMatchScore',
          'avgAutoKpa', 'avgTeleOpKpa', 'avgAutoGears','avgTeleOpGears', 'avgTotalFuel',
          'avgHoppers', 'sdAutoScore', 'sdTeleOpScore',
          'sdAutoScore','sdTeleOpScore', 'sdAutoKpa','sdTeleOpKpa','sdAutoGears',
          'sdMatchScore','sdTeleOpGears','sdTotalFuel',
          'takeoffAttemptPercentage','takeoffSuccessPercentage','takeoffAttemptSuccessPercentage',
          'pilotPlayPercentage', 'avgPointsPerCycle','sdPointsPerCycle','avgCycles',
          'sdCycles','reachBaselinePercentage','avgHighGoals','sdHighGoals','avgLowGoals',
          'sdLowGoals','autoShootsKey',
          'autoAbility', 'teleOpAbility', 'driveTeamAbility',
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
