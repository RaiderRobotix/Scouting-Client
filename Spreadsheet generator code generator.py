fields = ['teamNum','teamName','avgAutoScore', 'sdAutoScore','avgTeleOpScore',
           'sdTeleOpScore','avgMatchScore','sdMatchScore',
          'reachBaselinePercentage','avgAutoKpa', 'sdAutoKpa','avgTeleOpKpa', 'sdTeleOpKpa',
          'avgAutoGears','sdAutoGears','autoGearAttemptSuccessPercent','autoGearPegLoc','leftPegPercent',
          'rightPegPercent','centerPegPercent','totalLeftPegSuccess','totalRightPegSuccess',
          'totalCenterPegSuccess','avgTeleOpGears', 'sdTeleOpGears','avgDroppedGears',
          'avgHighGoals','sdHighGoals','avgLowGoals',
          'sdLowGoals',
          'avgHoppers','avgPointsPerCycle','sdPointsPerCycle','avgCycles',
          'sdCycles',
          'takeoffPercentage','takeoffAttemptPercentage','takeoffAttemptSuccessPercentage',
          'pilotPlayPercentage',
          'avgTeleOpKpaFuelFocus','avgTeleOpGearsGearFocus','fuelFocusPercent','gearFocusPercent','hasPickup','hasIntake','isActive','doNotPick',
            'frequentRobotCommentStr','frequentPilotCommentStr',
          'allComments']

header = 'String header = \"'
for field in fields:
    header+= field+','
header+='\\n\";'
fileContents = 'fileContents += '
for field in fields:
    fileContents+='report.'+field+'+COMMA+'

fileContents+='\'\\n\';'

print(header)
print('\n')
print(fileContents)
